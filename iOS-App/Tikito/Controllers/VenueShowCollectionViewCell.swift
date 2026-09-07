//
//  VenueShowCollectionViewCell.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//

import UIKit

class VenueShowCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var venueNameLabel: UILabel!
    
    @IBOutlet weak var venueAddress: UILabel!
    @IBOutlet weak var timingStackView: UIStackView!
    
    var timeButtonTapped: ((ShowTiming) -> Void)?

    func configure(
        venueName: String,
        address: String,
        timings: [ShowTiming]
    ) {
        venueNameLabel.text = venueName
        venueAddress.text = address

        // Remove old buttons
        timingStackView.arrangedSubviews.forEach {
            timingStackView.removeArrangedSubview($0)
            $0.removeFromSuperview()
        }

        // Create buttons for actual show timings
        for timing in timings {

            let button = UIButton(type: .system)

            button.setTitle(
                timing.showStartTime ?? "",
                for: .normal
            )

            button.setTitleColor(.label, for: .normal)

            button.layer.cornerRadius = 8
            button.layer.borderWidth = 1

            button.contentEdgeInsets = UIEdgeInsets(
                top: 8,
                left: 12,
                bottom: 8,
                right: 12
            )

            button.addAction(
                UIAction { [weak self] _ in
                    self?.timeButtonTapped?(timing)
                },
                for: .touchUpInside
            )

            timingStackView.addArrangedSubview(button)
        }
    }

    override func prepareForReuse() {
        super.prepareForReuse()

        venueNameLabel.text = nil
        venueAddress.text = nil

        timingStackView.arrangedSubviews.forEach {
            timingStackView.removeArrangedSubview($0)
            $0.removeFromSuperview()
        }

        timeButtonTapped = nil
    }
}
