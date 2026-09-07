//
//  CancelTicket.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

struct CancelTicket: Codable {
    let bookingId: Int64
    let bookingStatus: String
    let paymentStatus: String
}
