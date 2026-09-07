//
//  Event.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation

struct Event : Codable
{
    let eventId : Int64?
    let eventName : String?
    let eventType : String?
    let eventDescription : String?
    let eventDurationMin : Int64?
    let ageRestriction : Int?
    let posterUrl : String?
}
