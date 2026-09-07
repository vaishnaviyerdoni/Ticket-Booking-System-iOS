//
//  HomeViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import UIKit

class HomeViewController: UIViewController,
                          UICollectionViewDataSource,
                          UICollectionViewDelegate {

    @IBOutlet weak var EventCollectionView: UICollectionView!

    private var eventTypes: [EventType] = []
    private var selectedEventType : String?

    override func viewDidLoad() {
        super.viewDidLoad()

        // Collection View setup
        EventCollectionView.dataSource = self
        EventCollectionView.delegate = self

        // Load data from API
        loadEventTypes()
    }

    // MARK: - API

    private func loadEventTypes() {

        Task {
            do {
                let eventService = EventService()

                let response = try await eventService.getByEventCount()

                await MainActor.run {

                    self.eventTypes = response.data

                    print("HOME EVENT TYPES COUNT:", self.eventTypes.count)
                    print("HOME EVENT TYPES:", self.eventTypes)

                    self.EventCollectionView.reloadData()
                }

            } catch {
                print("HOME EVENT API ERROR:", error)
            }
        }
    }

    // MARK: - UICollectionViewDataSource

    func collectionView(
        _ collectionView: UICollectionView,
        numberOfItemsInSection section: Int
    ) -> Int {

        print("COLLECTION VIEW COUNT:", eventTypes.count)

        return eventTypes.count
    }

    func collectionView(
        _ collectionView: UICollectionView,
        cellForItemAt indexPath: IndexPath
    ) -> UICollectionViewCell {

        let cell = collectionView.dequeueReusableCell(
            withReuseIdentifier: "EventCell",
            for: indexPath
        ) as! EventCellCollectionViewCell

        let eventType = eventTypes[indexPath.item]

        let category = eventType.eventType ?? ""
        let liveText = "Live(\(eventType.count ?? 0))"

        cell.configure(
            category: category,
            liveText: liveText
        )

        cell.arrowTapped = { [weak self] in

            self?.selectedEventType = category

            self?.performSegue(
                withIdentifier: "fromHometoEventListType",
                sender: category
            )
        }

        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {

        if segue.identifier == "fromHometoEventListType" {

            let destination = segue.destination
                as! EventListByTypeViewController

            destination.eventType = sender as? String
        }
    }}
