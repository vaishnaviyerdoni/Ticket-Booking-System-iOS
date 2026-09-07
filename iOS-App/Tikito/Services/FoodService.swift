//
//  FoodService.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

class FoodService
{
    private let apiClient = APIClient()
    
    func getAvailableFood() async throws -> ApiResponse<[Food]>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/foods/available")!
        
        return try await apiClient.request(url: url, method: "GET")
    }
}
