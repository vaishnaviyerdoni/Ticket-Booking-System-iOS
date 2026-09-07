//
//  ShowResponse.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct ShowResponse: Codable
{
    let dates : [ShowDate]?
    let venues : [VenueShows]?
}
