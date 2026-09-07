//
//  VerifyPaymentRequest.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import Foundation
struct VerifyPaymentRequest : Codable
{
    let razorpayOrderId : String?
    let razorpayPaymentId: String?
    let razorpaySignature : String?
}
