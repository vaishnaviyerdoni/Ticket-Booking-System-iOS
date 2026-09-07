//
//  ShowListViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//
import UIKit

class ShowListViewController: UIViewController,
                              UICollectionViewDataSource,
                              UICollectionViewDelegate {

    var eventId: Int64?
    var eventName: String?

    @IBOutlet weak var posterImage: UIImageView!
    @IBOutlet weak var eventNameLabel: UILabel!
    @IBOutlet weak var DateCollectionView: UICollectionView!
    @IBOutlet weak var VenueCollectionView: UICollectionView!

    private var showResponse: ShowResponse?
    private var dates: [ShowDate] = []
    private var venues: [VenueShows] = []
    private var filteredVenues: [VenueShows] = []

    private var selectedDateIndex: Int = 0

    override func viewDidLoad() {
        super.viewDidLoad()

        print("SHOW SCREEN EVENT ID:", eventId ?? 0)
        print("SHOW SCREEN EVENT NAME:", eventName ?? "nil")

        eventNameLabel.text = eventName

        DateCollectionView.dataSource = self
        DateCollectionView.delegate = self

        VenueCollectionView.dataSource = self
        VenueCollectionView.delegate = self

        loadShows()
    }

    // MARK: - Load Shows

    private func loadShows() {

        guard let eventId = eventId else {
            print("SHOW EVENT ID IS NIL")
            return
        }

        Task {
            do {

                let showService = ShowService()

                let response = try await showService.getShowByEvent(
                    eventId: eventId
                )

                await MainActor.run {

                    self.showResponse = response.data

                    self.dates = response.data.dates ?? []
                    self.venues = response.data.venues ?? []

                    print("SHOW API STATUS:", response.status)
                    print("SHOW DATES COUNT:", self.dates.count)
                    print("SHOW VENUES COUNT:", self.venues.count)

                    print("SHOW DATES:", self.dates)
                    print("SHOW VENUES:", self.venues)

                    // Select first date
                    self.selectedDateIndex = 0

                    // Filter venues according to first date
                    self.filterVenuesBySelectedDate()

                    // Reload both collection views
                    self.DateCollectionView.reloadData()
                    self.VenueCollectionView.reloadData()
                }

            } catch {

                print("SHOW API ERROR:", error)
            }
        }
    }

    // MARK: - Filter Venues

    private func filterVenuesBySelectedDate() {

        guard !dates.isEmpty else {
            filteredVenues = []
            return
        }

        let selectedDate = dates[selectedDateIndex].showDate ?? ""

        filteredVenues = venues.filter { venue in

            venue.shows.contains { show in
                show.showDate == selectedDate
            }
        }

        print("SELECTED DATE:", selectedDate)
        print("FILTERED VENUES COUNT:", filteredVenues.count)
    }

    // MARK: - Collection View Data Source

    func collectionView(_ collectionView: UICollectionView,
                        numberOfItemsInSection section: Int) -> Int {

        if collectionView == DateCollectionView {
            return dates.count
        }

        if collectionView == VenueCollectionView {
            return filteredVenues.count
        }

        return 0
    }

    func collectionView(_ collectionView: UICollectionView,
                        cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {

        // MARK: Date Collection View

        if collectionView == DateCollectionView {

            let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: "ShowDateCell",
                for: indexPath
            ) as! ShowDateCollectionViewCell

            let date = dates[indexPath.item]

            cell.configure(
                day: date.day ?? "",
                date: date.month ?? "",
                selected: indexPath.item == selectedDateIndex
            )

            return cell
        }

        // MARK: Venue Collection View

        if collectionView == VenueCollectionView {

            let cell = collectionView.dequeueReusableCell(
                withReuseIdentifier: "VenueShowCell",
                for: indexPath
            ) as! VenueShowCollectionViewCell

            let venue = filteredVenues[indexPath.item]

            let selectedDate = dates[selectedDateIndex].showDate ?? ""

            // Only timings for selected date
            let timings = venue.shows.filter {
                $0.showDate == selectedDate
            }

            cell.configure(
                venueName: venue.venueName ?? "",
                address: venue.address ?? "",
                timings: timings
            )

            // Time button callback
            cell.timeButtonTapped = { [weak self] timing in
                
                self?.performSegue(
                    withIdentifier: "toSeatLayout",
                    sender: (
                        timing: timing,
                        venue: venue
                    )
                )
            }

            return cell
        }

        return UICollectionViewCell()
    }

    // MARK: - Date Selection

    func collectionView(_ collectionView: UICollectionView,
                        didSelectItemAt indexPath: IndexPath) {

        guard collectionView == DateCollectionView else {
            return
        }

        selectedDateIndex = indexPath.item

        let selectedDate = dates[indexPath.item]

        print(
            "SELECTED SHOW DATE:",
            selectedDate.showDate ?? "nil"
        )

        // Filter venues for newly selected date
        filterVenuesBySelectedDate()

        // Refresh date selection
        DateCollectionView.reloadData()

        // Refresh filtered venues and timings
        VenueCollectionView.reloadData()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {

        if segue.identifier == "toSeatLayout" {

            let destination = segue.destination as! SeatLayoutViewController

            if let data = sender as? (
                timing: ShowTiming,
                venue: VenueShows
            ) {

                destination.showId = data.timing.showId
                destination.venueId = data.venue.venueId

                destination.eventName = eventName
                destination.venueName = data.venue.venueName

                destination.showDate = data.timing.showDate
                destination.showTime = data.timing.showStartTime
                destination.price = data.timing.price
            }
        }
    }}
