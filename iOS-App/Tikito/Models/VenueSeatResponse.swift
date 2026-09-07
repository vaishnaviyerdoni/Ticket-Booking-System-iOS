//
//  VenueSeatResponse.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 03/09/26.
//

import Foundation

struct VenueSeatResponse: Codable {
    let venueId: Int64?
    let name: String?
    let address: String?
    let seatCapacity: Int?
    let seatList: [SeatItem]?
}
