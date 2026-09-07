//
//  EventCellCollectionViewCell.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//

import UIKit

class EventCellCollectionViewCell: UICollectionViewCell {

    @IBOutlet weak var CategoryLabel: UILabel!
    @IBOutlet weak var liveLabel: UILabel!
    @IBOutlet weak var arroeBtn: UIButton!

    var arrowTapped: (() -> Void)?

    func configure(category: String, liveText: String) {
        CategoryLabel.text = category
        liveLabel.text = liveText
    }

    @IBAction func arrowButtonClicked(_ sender: UIButton) {
        arrowTapped?()
    }

    override func prepareForReuse() {
        super.prepareForReuse()

        CategoryLabel.text = nil
        liveLabel.text = nil
        arrowTapped = nil
    }
}
