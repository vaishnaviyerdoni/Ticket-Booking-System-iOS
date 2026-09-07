//
//  SeatItem.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct SeatItem: Codable
{
    let seatId: Int64?
    let seatNo: String?
    
    var isSelected: Bool
    var isBooked : Bool
   
    init(seatId: Int64, SeatNo: String, isSelected: Bool, isBooked: Bool)
    {
        self.seatId = seatId
        self.seatNo = SeatNo
        self.isSelected = isSelected
        self.isBooked = isBooked
    }
    
    enum SeatStatus: String, Codable
    {
        case available = "AVAILABLE"
        case booked = "BOOKED"
        case selected = "SELECTED"
    }
    
    enum CodingKeys: String, CodingKey
    {
        case seatId
        case seatNo
    }

    init(from decoder : Decoder) throws
    {
        let container = try decoder.container(keyedBy:CodingKeys.self)
        
        seatId = try container.decode(Int64.self, forKey: .seatId)
        seatNo = try container.decode(String.self, forKey: .seatNo)
        
        
        isSelected = false
        isBooked = false
    }
}
