//
//  UserBooking.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

struct UserBooking: Codable {
    let bookingId: Int64
    let showId: Int64
    let eventName: String
    let venueName: String
    let showDate: String
    let showStartTime: String
    let showEndTime: String
    let totalAmt: Double
    let seatNumbers: [String]
    let paymentStatus: String
    let bookingStatus: String
    let bookingDate: String
}
