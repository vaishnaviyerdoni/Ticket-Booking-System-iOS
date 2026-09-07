//
//  Show.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct Show: Codable
{
    let showId : Int64?
    let venueId: Int64?
    let eventId: Int64?
    let language : String?
    let price : Double?
    let isEighteenPlus : Bool
    let showDate : Date?
    let showStartTime : Date?
    let showEndTime : Date?
}
