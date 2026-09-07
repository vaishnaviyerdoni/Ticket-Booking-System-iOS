//
//  APIClient.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation

class APIClient
{
    //GET
    func request<T: Decodable>(url: URL, method: String="GET", headers: [String:String] = [:]) async throws -> T
    {
        
        var request = URLRequest(url: url)
        
        request.httpMethod = method
        
        //ADDINH JWT TOKEN
        if let token = KeychainService().getToken()
        {
            request.setValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        }
        
        if let token = KeychainService().getToken()
        {
            print("JWT FOUND IN KEYCHAIN")
            print("JWT:", token)

            request.setValue(
                "Bearer \(token)",
                forHTTPHeaderField: "Authorization"
            )
        }
        else
        {
            print("NO JWT FOUND IN KEYCHAIN")
        }
        for(key, value) in headers{
            request.setValue(value, forHTTPHeaderField: key)
        }
        
        let(data, response) = try await URLSession.shared.data(for:request)
        
        guard let httpResponse = response as? HTTPURLResponse else
        {
            throw APIError.invalidResponse
        }
        
        guard (200...299).contains(httpResponse.statusCode) else
        {
            throw APIError.httpError(httpResponse.statusCode)
        }
        
        do
        {
            return try JSONDecoder().decode(T.self, from:data)
        }
        catch
        {
            throw APIError.decodingError(error)
        }
    }
    
    //POST
    func request<T: Decodable, Body:Encodable>(url: URL,
                                               method: String="POST",
                                               body: Body,
                                               headers: [String:String] = [:])
    async throws -> T
    {
        let config = URLSessionConfiguration.default
        config.timeoutIntervalForRequest = 120
        config.timeoutIntervalForResource = 120
        var request = URLRequest(url: url)
        
        request.httpMethod = method
        
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        for(key, value) in headers{
            request.setValue(value, forHTTPHeaderField: key)
        }
        
        request.httpBody = try JSONEncoder().encode(body)
        
        let session = URLSession(configuration: config)
        let(data, response) = try await session.data(for:request)
        
        guard let httpResponse = response as? HTTPURLResponse else
        {
            throw APIError.invalidResponse
        }
        
        guard (200...299).contains(httpResponse.statusCode) else
        {
            throw APIError.httpError(httpResponse.statusCode)
        }
        
        do
        {
            return try JSONDecoder().decode(T.self, from:data)
        }
        catch
        {
            throw APIError.decodingError(error)
        }
    }
    
    // PATCH
    func patch<T: Decodable, Body: Encodable>(
        url: URL,
        method: String = "PATCH",
        body: Body,
        headers: [String: String] = [:]
    ) async throws -> T
    {
        let config = URLSessionConfiguration.default
        config.timeoutIntervalForRequest = 120
        config.timeoutIntervalForResource = 120

        var request = URLRequest(url: url)

        request.httpMethod = method

        request.setValue(
            "application/json",
            forHTTPHeaderField: "Content-Type"
        )


        // ADD JWT TOKEN
        if let token = KeychainService().getToken()
        {
            print("JWT FOUND IN KEYCHAIN")
            print("JWT:", token)

            request.setValue(
                "Bearer \(token)",
                forHTTPHeaderField: "Authorization"
            )
        }
        else
        {
            print("NO JWT FOUND IN KEYCHAIN")
        }


        // Additional headers
        for (key, value) in headers
        {
            request.setValue(value, forHTTPHeaderField: key)
        }


        request.httpBody = try JSONEncoder().encode(body)

        let session = URLSession(configuration: config)

        let (data, response) = try await session.data(for: request)


        guard let httpResponse = response as? HTTPURLResponse else
        {
            throw APIError.invalidResponse
        }


        guard (200...299).contains(httpResponse.statusCode) else
        {
            throw APIError.httpError(httpResponse.statusCode)
        }


        do
        {
            return try JSONDecoder().decode(T.self, from: data)
        }
        catch
        {
            throw APIError.decodingError(error)
        }
    }
    
    //PUT
    func put<T: Decodable, Body:Encodable>(url: URL,
                                             method: String="PUT",
                                             body: Body,
                                             headers: [String:String] = [:])
    async throws -> T
    {
        let config = URLSessionConfiguration.default
        config.timeoutIntervalForRequest = 120
        config.timeoutIntervalForResource = 120
        var request = URLRequest(url: url)
        
        request.httpMethod = method
        
        //ADDINH JWT TOKEN
        if let token = KeychainService().getToken()
        {
            request.setValue("Bearer \(token)", forHTTPHeaderField: "Authorization")
        }
        
        if let token = KeychainService().getToken()
        {
            print("JWT FOUND IN KEYCHAIN")
            print("JWT:", token)

            request.setValue(
                "Bearer \(token)",
                forHTTPHeaderField: "Authorization"
            )
        }
        else
        {
            print("NO JWT FOUND IN KEYCHAIN")
        }
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        for(key, value) in headers{
            request.setValue(value, forHTTPHeaderField: key)
        }
        
        request.httpBody = try JSONEncoder().encode(body)
        
        let session = URLSession(configuration: config)
        let(data, response) = try await session.data(for:request)
        
        guard let httpResponse = response as? HTTPURLResponse else
        {
            throw APIError.invalidResponse
        }
        
        guard (200...299).contains(httpResponse.statusCode) else
        {
            throw APIError.httpError(httpResponse.statusCode)
        }
        
        do
        {
            return try JSONDecoder().decode(T.self, from:data)
        }
        catch
        {
            throw APIError.decodingError(error)
        }
    }
    
    //DELETE
    func delete<T: Decodable>(url: URL, method: String="DELETE")
    async throws -> T
    {
        let config = URLSessionConfiguration.default
        config.timeoutIntervalForRequest = 120
        config.timeoutIntervalForResource = 120
        var request = URLRequest(url: url)
        
        request.httpMethod = method
        
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        
        let session = URLSession(configuration: config)
        let(data, response) = try await session.data(for:request)
        
        guard let httpResponse = response as? HTTPURLResponse else
        {
            throw APIError.invalidResponse
        }
        
        guard (200...299).contains(httpResponse.statusCode) else
        {
            throw APIError.httpError(httpResponse.statusCode)
        }
        
        do
        {
            return try JSONDecoder().decode(T.self, from:data)
        }
        catch
        {
            throw APIError.decodingError(error)
        }
    }
}
