//
//  ShowDateCollectionViewCell.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//

import UIKit

class ShowDateCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var dayLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!

    func configure(day: String, date: String, selected: Bool) {
        dayLabel.text = day
        dateLabel.text = date

        if selected {
            layer.borderWidth = 2
            layer.cornerRadius = 8
        } else {
            layer.borderWidth = 0
            layer.cornerRadius = 8
        }
    }

    override func prepareForReuse() {
        super.prepareForReuse()

        dayLabel.text = nil
        dateLabel.text = nil
        layer.borderWidth = 0
    }
}
