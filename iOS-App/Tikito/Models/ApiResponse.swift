//
//  ApiResponse.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

struct ApiResponse<T: Codable> : Codable
{
    let status : String
    let data : T
}
