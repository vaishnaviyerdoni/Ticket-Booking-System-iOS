//
//  VenueShows.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct VenueShows : Codable
{
    let venueId: Int64?
    let venueName : String?
    let address : String?
    let areFacilitiesAvailable: Bool
    var shows : [ShowTiming] = []
    var filteredShows : [ShowTiming] = []
    
    enum CodingKeys: String, CodingKey
    {
        case venueId
        case venueName
        case address
        case areFacilitiesAvailable
        case shows
    }
}
