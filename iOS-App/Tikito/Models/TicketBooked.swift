//
//  TicketBooked.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

struct TicketBooked: Codable {
    let bookingId: Int64
    let showId: Int64
    let eventName: String
    let venueName: String
    let showDate: String
    let showStartTime: String
    let seatNums: [String]
    let totalAmt: Double
    let paymentStatus: String
    let bookingStatus: String
}
