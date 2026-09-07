//
//  EventService.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

class EventService
{
    private let apiClient = APIClient()
    
    //1 - get all events
    func getAllEvents() async throws -> ApiResponse<[Event]>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/events")!
        
        return try await apiClient.request(url: url, method: "GET")
    }
    
    //2 - get event by type
    func getEventByType(eventType: String) async throws -> ApiResponse<[Event]>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/events/type/\(eventType)")!
        
        return try await apiClient.request(url: url, method: "GET")
    }
    
    //3 - get eventbyid
    func getEventById(eventId: Int64) async throws -> ApiResponse<Event>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/events/\(eventId)")!
        
        return try await apiClient.request(url: url, method: "GET")
    }
    
    //4 - getByEventCount
    func getByEventCount() async throws -> ApiResponse<[EventType]>
    {
        let url = URL(string: "https://tikito.onrender.com/tikito/events/count-by-type")!
        
        return try await apiClient.request(url: url, method: "GET")    }
}
