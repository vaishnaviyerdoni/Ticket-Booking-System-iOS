//
//  LoginViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import UIKit

class LoginViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        //to hide and unhide the password when user is entering it
        PasswordTextField.isSecureTextEntry = true
        let eyeButton = UIButton(type: .system)
        
        eyeButton.setImage(UIImage(systemName: "eye"), for: .normal)
        eyeButton.setImage(UIImage(systemName: "eye.slash"), for: .selected)
        
        eyeButton.addTarget(self, action: #selector(togglePassowrdVisibility), for: .touchUpInside)
        
        PasswordTextField.rightView = eyeButton
        PasswordTextField.rightViewMode = .always
        
        let keychain = KeychainService()

        keychain.saveToken("TEST_JWT_123")

        if let token = keychain.getToken() {
            print("KEYCHAIN READ:", token)
        }

        keychain.deleteToken()

        if let token = keychain.getToken() {
            print("TOKEN STILL EXISTS:", token)
        } else {
            print("KEYCHAIN DELETE SUCCESS")
        }
    }
    
    @IBOutlet weak var LoginBtn: UIButton!
    
    @IBOutlet weak var PasswordTextField: UITextField!
    
    @IBOutlet weak var EmailTextField: UITextField!
    
    @IBAction func LoginButtonClicked(_ sender: Any)
    {
        LoginBtn.isEnabled = false
        
        Task {
            @MainActor in
            
            do {
                let authser = AuthService()
                
                // 1. Login
                let response = try await authser.login(
                    email: EmailTextField.text ?? "",
                    password: PasswordTextField.text ?? ""
                )
                
                print("Login Successful")
                print("status: \(response.status)")
                
                print("----------------------------")
                testEvents()
                print("----------------------------")
                
                let user = response.data
                
                // 2. Get JWT
                guard let token = user.jwtToken else {
                    print("JWT token missing")
                    LoginBtn.isEnabled = true
                    return
                }
                
                print("JWT received")
                
                // 3. Save JWT
                let keychain = KeychainService()
                keychain.saveToken(token)
                
                print("JWT saved to Keychain")
                
                // 4. Test protected API
                let profileResponse = try await authser.getProfile()
                
                print("Profile status:", profileResponse.status)
                print("Profile:", profileResponse.data)
                
                LoginBtn.isEnabled = true
            }
            catch {
                print("Login Failed:", error)
                LoginBtn.isEnabled = true
            }
        }
    }
    
    @IBAction func RegisterButtonClicked(_ sender: Any)
    {
        
    }
    
    //to hide and unhide the password when user is entering it
    @objc
    func togglePassowrdVisibility(_ sender: UIButton)
    {
        sender.isSelected.toggle()
        PasswordTextField.isSecureTextEntry.toggle()
    }
    
    func testEvents()
    {
        Task
        {
            do
            {
                let eventSer = EventService()
                //let showSer =  ShowService()
                //let venueSer = VenueService()
                //let foodSer = FoodService()
                
                //let response = try await eventSer.getAllEvents()
                let response = try await eventSer.getEventByType(eventType: "MOVIE")
                //let response = try await eventSer.getEventById(eventId: 1)
                //let response = try await eventSer.getByEventCount()
                //let response = try await showSer.getShowByEvent(eventId: 1)
                //let response = try await venueSer.getVenueById(venueId: 2)
                //let response = try await foodSer.getAvailableFood()
                print("STATUS: ", response.status)
                print("DATA: ", response.data)
            }
            catch
            {
                print("Event error - ", error)
            }
        }
        //
        
        /*
         // MARK: - Navigation
         
         // In a storyboard-based application, you will often want to do a little preparation before navigation
         override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
         // Get the new view controller using segue.destination.
         // Pass the selected object to the new view controller.
         }
         */
    }
}
