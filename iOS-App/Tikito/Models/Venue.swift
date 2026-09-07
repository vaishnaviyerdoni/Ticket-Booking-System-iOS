//
//  Venue.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct Venue : Codable
{
    let venueId: Int64?
    let name: String?
    let address : String?
    let isFacilitiesAvailable : Bool?
    let seatCapacity : Int?
    let seats: [SeatItem]?
    
    enum CodingKeys: String, CodingKey
    {
        case venueId
        case name
        case address
        case seatCapacity
        case isFacilitiesAvailable = "areFacilitiesAvailable"
        case seats = "SeatList"
    }
    
    init(from decoder : Decoder) throws
    {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        venueId = try container.decode(Int64.self, forKey: .venueId)
        name = try container.decode(String.self, forKey: .name)
        address = try container.decodeIfPresent(String.self, forKey: .address)
        isFacilitiesAvailable = try container.decodeIfPresent(Bool.self, forKey: .isFacilitiesAvailable)
        seatCapacity = try container.decodeIfPresent(Int.self, forKey: .seatCapacity)
        seats = try container.decodeIfPresent([SeatItem].self, forKey: .seats)
    }
}
