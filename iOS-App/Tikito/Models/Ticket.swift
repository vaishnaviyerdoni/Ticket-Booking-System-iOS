//
//  Ticket.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct Ticket : Codable
{
    let showId : Int64?
    let seatIds : [Int64]
}
