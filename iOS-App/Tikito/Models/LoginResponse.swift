//
//  LoginResponse.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct LoginResponse: Codable
{
    let jwtToken: String?
    let userId: Int64?
    let email : String?
    let firstName : String?
    let lastName : String?
    let role : String?
}
