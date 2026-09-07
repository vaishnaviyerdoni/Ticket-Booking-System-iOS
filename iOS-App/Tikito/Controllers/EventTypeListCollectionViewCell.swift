//
//  EventTypeListCollectionViewCell.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//

import UIKit

class EventTypeListCollectionViewCell: UICollectionViewCell
{
    @IBOutlet weak var eventName: UILabel!
    
    @IBOutlet weak var eventType: UILabel!
    
    @IBOutlet weak var eventDescription: UILabel!
    
    @IBOutlet weak var bookBtn: UIButton!
    
    var bookTapped: (() -> Void)?
    
    func configure(name: String, type: String, description: String) {
        eventName.text = name
        eventType.text = type
        eventDescription.text = description
    }
    
    @IBAction func bookButtonClicked(_ sender: UIButton)
    {
        bookTapped?()
    }
    
    override func prepareForReuse()
    {
        super.prepareForReuse()
        eventName.text = nil
        eventType.text = nil
        eventDescription.text = nil
    }
}
