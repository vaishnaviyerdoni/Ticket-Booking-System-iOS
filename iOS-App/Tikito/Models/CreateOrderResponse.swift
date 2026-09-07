//
//  CreateOrderResponse.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation

struct CreateOrderResponse: Codable
{
    let orderId : String?
    let amount :  Int?
    let currency : String?
    let keyId : String?
}
