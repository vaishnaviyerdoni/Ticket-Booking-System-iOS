//
//  EventListByTypeViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//


import UIKit

class EventListByTypeViewController: UIViewController,
                                     UICollectionViewDataSource,
                                     UICollectionViewDelegate {

    var eventType: String?

    @IBOutlet weak var EventTypeCollection: UICollectionView!

    private var events: [Event] = []

    override func viewDidLoad() {
        super.viewDidLoad()

        print("SELECTED EVENT TYPE: \(eventType ?? "nil")")

        EventTypeCollection.dataSource = self
        EventTypeCollection.delegate = self

        loadEvents()
    }

    // MARK: - API

    private func loadEvents() {

        guard let eventType = eventType, !eventType.isEmpty else {
            print("EVENT TYPE IS NIL OR EMPTY")
            return
        }

        Task {
            do {
                let eventService = EventService()

                let response = try await eventService.getEventByType(
                    eventType: eventType
                )

                await MainActor.run {

                    self.events = response.data

                    print("EVENTS BY TYPE STATUS:", response.status)
                    print("EVENTS BY TYPE:", self.events)
                    print("EVENTS COUNT:", self.events.count)

                    self.EventTypeCollection.reloadData()
                }

            } catch {
                print("EVENTS BY TYPE API ERROR:", error)
            }
        }
    }

    // MARK: - UICollectionViewDataSource

    func collectionView(
        _ collectionView: UICollectionView,
        numberOfItemsInSection section: Int
    ) -> Int {

        return events.count
    }

    func collectionView(
        _ collectionView: UICollectionView,
        cellForItemAt indexPath: IndexPath
    ) -> UICollectionViewCell {

        let cell = collectionView.dequeueReusableCell(
            withReuseIdentifier: "EventTypeListCell",
            for: indexPath
        ) as! EventTypeListCollectionViewCell

        let event = events[indexPath.item]

        let name = event.eventName ?? ""
        let type = event.eventType ?? ""
        let description = event.eventDescription ?? ""

        cell.configure(
            name: name,
            type: type,
            description: description
        )

        cell.bookTapped = { [weak self] in

            self?.performSegue(
                withIdentifier: "toShowList",
                sender: event
            )
        }

        return cell
    }

    // MARK: - Navigation

    override func prepare(
        for segue: UIStoryboardSegue,
        sender: Any?
    ) {

        if segue.identifier == "toShowList" {

            let destination = segue.destination
                as! ShowListViewController

            if let event = sender as? Event {

                destination.eventId = event.eventId
                destination.eventName = event.eventName
            }
        }
    }
}
