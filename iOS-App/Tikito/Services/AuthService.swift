//
//  AuthService.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation

class AuthService
{
    let apiClient = APIClient()
        
    //login
    func login(email:String, password:String) async throws -> ApiResponse<LoginResponse>
    {
        let url = URL(string:"https://tikito.onrender.com/tikito/auth/login")!
        let request = LoginRequest(email: email, password: password)
        
        return try await apiClient.request(url: url, method: "POST", body: request)
    }
    
    //registration
    func register(firstName: String,
                  lastName: String,
                  email: String,
                  password : String,
                  phone : String) async throws -> ApiResponse<UserDto>
    {
        let url = URL(string:"https://tikito.onrender.com/tikito/user/register")!
        let request = UserDto(userId:nil,
                              firstName: firstName,
                              lastName: lastName,
                              email : email,
                              password : password,
                              phone  : phone,
                              role : nil,
                              oldPassword : nil,
                              newPassword: nil,
                              imageName : nil,
                              otp : nil)
        
        return try await apiClient.request(url: url, method: "POST", body: request)
    }
        
    // get profile
    func getProfile() async throws -> ApiResponse<UserDto>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/user/profile")!
        
        return try await apiClient.request(url: url, method: "GET")
    }
    
    //update paasword
    func updatePassword(oldPassword:String, newPassword: String) async throws -> ApiResponse<String>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/user/password")!
        
        let request = UserDto(userId:nil,
                              firstName: nil,
                              lastName: nil,
                              email : nil,
                              password : nil,
                              phone  : nil,
                              role : nil,
                              oldPassword : oldPassword,
                              newPassword: newPassword,
                              imageName : nil,
                              otp : nil)
        
        return try await apiClient.put(url: url, method: "PUT", body: request)
    }
    
    //forget paasword
    func forgetPassword(email:String) async throws -> ApiResponse<String>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/user/forgot-password")!
        
        let request = UserDto(userId:nil,
                              firstName: nil,
                              lastName: nil,
                              email : email,
                              password : nil,
                              phone  : nil,
                              role : nil,
                              oldPassword : nil,
                              newPassword: nil,
                              imageName : nil,
                              otp : nil)
        
            return try await apiClient.request(url: url, method: "POST", body: request)
    }
    
    //reset paasword
    func resetPassword(email:String, otp:Int, newPassword:String) async throws -> ApiResponse<UserDto>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/user/forgot-password")!
        
        let request = UserDto(userId:nil,
                              firstName: nil,
                              lastName: nil,
                              email : email,
                              password : nil,
                              phone  : nil,
                              role : nil,
                              oldPassword : nil,
                              newPassword: newPassword ,
                              imageName : nil,
                              otp : otp)
        
        return try await apiClient.request(url: url, method: "POST", body: request)
    }}
