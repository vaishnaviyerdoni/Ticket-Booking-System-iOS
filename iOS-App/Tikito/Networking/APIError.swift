//
//  APIError.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation

enum APIError : Error
{
    case invalidURL
    case invalidResponse
    case httpError(Int)
    case decodingError(Error)
    case networkError(Error)
    case loginFailed
}
