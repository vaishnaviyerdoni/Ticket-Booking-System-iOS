//
//  BookingService.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

class BookingService {

    let apiClient = APIClient()

    // GET Available Seats
    func getAvailableSeats(
        showId: Int64
    ) async throws -> ApiResponse<[AvailableSeat]> {

        let url = URL(
            string: "https://tikito.onrender.com/tikito/booking/admin/getAvailableSeats?showId=\(showId)"
        )!

        return try await apiClient.request(
            url: url,
            method: "GET"
        )
    }

    // POST Book Ticket
    func bookTicket(showId: Int64, seatIds: [Int64]) async throws -> ApiResponse<TicketBooked>
    {

        let keyChain = KeychainService()
        let url = URL(string: "https://tikito.onrender.com/tikito/booking/user")!

        let request = Ticket(showId: showId,seatIds: seatIds)
        
        guard let token = keyChain.getToken() else
        {
            throw APIError.httpError(401)
        }
        
        let headers = ["Authorization" : "Bearer \(token)"]
    
        return try await apiClient.request(url: url,method: "POST",body: request, headers: headers)
    }

    // GET All My Bookings
    func getMyBookings()
        async throws -> ApiResponse<[UserBooking]> {

        let url = URL(
            string: "https://tikito.onrender.com/tikito/booking/user/getMyBooking"
        )!

        return try await apiClient.request(
            url: url,
            method: "GET"
        )
    }

    // PATCH Cancel Booking
    func cancelBooking(
        bookingId: Int64
    ) async throws -> ApiResponse<CancelTicket> {

        let url = URL(
            string: "https://tikito.onrender.com/tikito/booking/user/cancel/\(bookingId)"
        )!

        return try await apiClient.request(
            url: url,
            method: "PATCH"
        )
    }
}
