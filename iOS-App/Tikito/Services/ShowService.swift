//
//  ShowService.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

class ShowService
{
    private let apiClient = APIClient()
    
    //get shows by event type
    func getShowByEvent(eventId: Int64) async throws -> ApiResponse<ShowResponse>
    {
        let url = URL(string : "https://tikito.onrender.com/tikito/shows/event/\(eventId)")!
        
        return try await apiClient.request(url: url, method: "GET")
    }
}
