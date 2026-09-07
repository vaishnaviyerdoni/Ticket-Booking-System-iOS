//
//  UserDto.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct UserDto : Codable
{
    let userId : Int64?
    let firstName : String?
    let lastName : String?
    let email : String?
    let password : String?
    let phone : String?
    let role : String?
    let oldPassword : String?
    let newPassword : String?
    let imageName: String?
    let otp : Int?
}
