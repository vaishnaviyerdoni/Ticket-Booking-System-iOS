//
//  SeatCollectionViewCell.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 03/09/26.
//

import UIKit

class SeatCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var seatLabel: UILabel!

    func configure(seat: SeatItem) {

        seatLabel.text = seat.seatNo

        if seat.isBooked {
            seatLabel.backgroundColor = .systemGray
            seatLabel.textColor = .white
        }
        else if seat.isSelected {
            seatLabel.backgroundColor = .systemYellow
            seatLabel.textColor = .black
        }
        else {
            seatLabel.backgroundColor = .systemGreen
            seatLabel.textColor = .black
        }
    }
}
