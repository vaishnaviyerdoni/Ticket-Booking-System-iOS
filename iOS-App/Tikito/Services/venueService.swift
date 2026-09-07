//
//  venueService.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

class VenueService {

    private let apiClient = APIClient()

    func getVenueById(
        venueId: Int64
    ) async throws -> ApiResponse<VenueSeatResponse> {

        let url = URL(
            string: "https://tikito.onrender.com/tikito/venue/\(venueId)"
        )!

        return try await apiClient.request(
            url: url,
            method: "GET"
        )
    }
}
