//
//  CreateOrderRequest.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation

struct CreateOrderRequest : Codable
{
    let Amount : Double?
    let receipt : String?
    let currency : String? 
}
