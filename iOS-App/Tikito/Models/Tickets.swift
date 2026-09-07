//
//  Tickets.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct Tickets : Codable
{
    let bookingId: Int64?
    let amount : Double?
    let paymentStatus : String?
    let BookingStatus : String?
    let date : Date?
}
