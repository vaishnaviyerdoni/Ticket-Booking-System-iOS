//
//  Food.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct Food : Codable
{
    let foodId : Int64?
    let foodName : String?
    let description : String?
    let imageUrl : String?
    let price : Double?
    let available: Bool
    var quantity:Int = 0
    
    enum CodingKeys: String, CodingKey
    {
        case foodId
        case foodName
        case description
        case imageUrl
        case price
        case available
    }
    
    init(foodId:Int64? = nil,
         foodName:String? = nil,
         description:String? = nil,
         imageUrl:String? = nil,
         price:Double? = nil,
         available:Bool? = nil,
         quantity: Int = 0)
    {
        self.foodId = foodId
        self.foodName = foodName
        self.description = description
        self.imageUrl = imageUrl
        self.price = price
        self.available = available ?? false
        self.quantity = quantity
    }
    
    init(from decoder : Decoder) throws
    {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        
        foodId = try container.decodeIfPresent(Int64.self, forKey: .foodId)
        foodName = try container.decode(String.self, forKey: .foodName)
        description = try container.decodeIfPresent(String.self, forKey: .description)
        imageUrl = try container.decodeIfPresent(String.self, forKey: .imageUrl)
        price = try container.decodeIfPresent(Double.self, forKey: .price)
        available = try container.decodeIfPresent(Bool.self, forKey: .available) ?? false
        quantity = 0
    }
}
