//
//  Todo.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 01/09/26.
//

import Foundation

struct Todo: Codable
{
    let userId: Int
    let id: Int
    let title: String
    let completed: Bool
}
