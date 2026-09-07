//
//  BookingHistory.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation

struct BookingHistory: Codable
{
    let bookingId : Int64?
    let showId : Int64?
    let eventName : String?
    let venueName : String?
    let showDate : Date?
    let showStartTime : Date?
    let showEndTime : Date?
    let totalAmt : Double?
    let paymentStatus : String?
    let bookingStatus : String?
}
