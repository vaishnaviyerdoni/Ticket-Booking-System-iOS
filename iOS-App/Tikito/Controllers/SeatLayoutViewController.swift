//
//  SeatLayoutViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//
import UIKit

class SeatLayoutViewController: UIViewController,
                                UICollectionViewDataSource,
                                UICollectionViewDelegate,
                                UICollectionViewDelegateFlowLayout
{
    
    
    
    @IBOutlet weak var SeatCollectionView: UICollectionView!
    
    var showId: Int64?
    var venueId: Int64?
    
    var eventName: String?
    var venueName: String?
    
    @IBOutlet weak var eventNameLabel: UILabel!
    var showDate: String?
    var showTime: String?
    var price: Double?
    
    
    @IBOutlet weak var confirmButton: UIButton!
    
    private var seats: [SeatItem] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        print("SEAT LAYOUT")
        print("EVENT:", eventName ?? "nil")
        print("VENUE:", venueName ?? "nil")
        print("VENUE ID:", venueId ?? 0)
        print("SHOW ID:", showId ?? 0)
        print("DATE:", showDate ?? "nil")
        print("TIME:", showTime ?? "nil")
        print("PRICE:", price ?? 0)
        
        eventNameLabel.text = eventName
        
        SeatCollectionView.dataSource = self
        SeatCollectionView.delegate = self
        
        loadVenueSeats()
    }
    
    
    @IBAction func confirmButtonTapped(_ sender: UIButton) {

        let selectedSeatIds = seats
            .filter { $0.isSelected }
            .compactMap { $0.seatId }

        guard !selectedSeatIds.isEmpty else {
            print("NO SEATS SELECTED")
            return
        }

        guard let showId = showId else {
            print("SHOW ID IS NIL")
            return
        }

        print("BOOKING SHOW ID:", showId)
        print("BOOKING SEAT IDS:", selectedSeatIds)

        Task {
            do {

                let bookingService = BookingService()

                let response = try await bookingService.bookTicket(
                    showId: showId,
                    seatIds: selectedSeatIds
                )

                await MainActor.run {

                    print("BOOKING API STATUS:", response.status)
                    print("BOOKING RESPONSE:", response.data)
                    
                    if(response.status == "success")
                    {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 2.0)
                        {
                            self.performSegue(withIdentifier: "toConfirmBooking", sender: nil)
                        }
                    }

                }

            } catch {

                print("BOOKING API ERROR:", error)
            }
        }
    }
    
    // MARK: - Load Venue Seats
    
    private func loadVenueSeats() {
        
        guard let venueId = venueId else {
            print("VENUE ID IS NIL")
            return
        }
        
        Task {
            do {
                
                let venueService = VenueService()
                
                let response = try await venueService.getVenueById(
                    venueId: venueId
                )
                
                await MainActor.run {
                    
                    let venue = response.data
                    
                    self.seats = venue.seatList ?? []
                    
                    print("VENUE API STATUS:", response.status)
                    print("VENUE NAME:", venue.name ?? "")
                    print("SEAT COUNT:", self.seats.count)
                    print("SEATS:", self.seats)
                    
                    // After getting all venue seats,
                    // load which seats are available.
                    self.loadAvailableSeats()
                }
                
            } catch {
                
                print("VENUE API ERROR:", error)
            }
        }
    }
    
    // MARK: - Load Available Seats
    
    private func loadAvailableSeats() {
        
        guard let showId = showId else {
            print("SHOW ID IS NIL")
            return
        }
        
        Task {
            do {
                
                let bookingService = BookingService()
                
                let response = try await bookingService.getAvailableSeats(
                    showId: showId
                )
                
                await MainActor.run {
                    
                    print(
                        "AVAILABLE SEATS API STATUS:",
                        response.status
                    )
                    
                    print(
                        "AVAILABLE SEATS COUNT:",
                        response.data.count
                    )
                    
                    print(
                        "AVAILABLE SEATS:",
                        response.data
                    )
                    
                    // Initially mark every seat as booked.
                    for index in self.seats.indices {
                        self.seats[index].isBooked = true
                        self.seats[index].isSelected = false
                    }
                    
                    // Seats returned by API are available.
                    for availableSeat in response.data {
                        
                        if let index = self.seats.firstIndex(
                            where: {
                                $0.seatId == availableSeat.seatId
                            }
                        ) {
                            self.seats[index].isBooked = false
                        }
                    }
                    
                    print("FINAL SEAT STATE:")
                    
                    for seat in self.seats {
                        print(
                            seat.seatNo ?? "",
                            "BOOKED:",
                            seat.isBooked
                        )
                    }
                    
                    self.SeatCollectionView.reloadData()
                }
                
            } catch {
                
                print(
                    "AVAILABLE SEATS API ERROR:",
                    error
                )
            }
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return seats.count
    }
    
    func collectionView(_ collectionView: UICollectionView,
                        cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(
            withReuseIdentifier: "SeatCell",
            for: indexPath
        ) as! SeatCollectionViewCell
        
        let seat = seats[indexPath.item]
        
        //cell.seatLabel.text = seat.seatNo
        cell.configure(seat: seat)
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView,
                        didSelectItemAt indexPath: IndexPath) {
        
        guard !seats[indexPath.item].isBooked else {
            return
        }
        
        seats[indexPath.item].isSelected.toggle()
        
        collectionView.reloadItems(at: [indexPath])
        
        let selectedSeats = seats.filter {
            $0.isSelected
        }
        
        print("SELECTED SEATS:", selectedSeats.map {
            $0.seatNo ?? ""
        })
    }
    
    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        let columns: CGFloat = 5
        let spacing: CGFloat = 10
        
        let totalSpacing = spacing * (columns - 1)
        let width = (collectionView.bounds.width - totalSpacing) / columns
        
        return CGSize(width: width, height: width)
    }
}
