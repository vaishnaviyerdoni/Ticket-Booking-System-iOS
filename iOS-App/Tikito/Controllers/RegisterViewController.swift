//
//  RegisterViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import UIKit

class RegisterViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        PasswordTextField.isSecureTextEntry = true
        let eyeButton = UIButton(type: .system)
        
        eyeButton.setImage(UIImage(systemName: "eye"), for: .normal)
        eyeButton.setImage(UIImage(systemName: "eye.slash"), for: .selected)
        
        eyeButton.addTarget(self, action: #selector(togglePassowrdVisibility), for: .touchUpInside)
        
        PasswordTextField.rightView = eyeButton
        PasswordTextField.rightViewMode = .always
    }
    
    let authSer = AuthService()
    
    @IBOutlet weak var FirstNameTextField: UITextField!
    
    @IBOutlet weak var LastNameTextField: UITextField!
    
    @IBOutlet weak var EmailTextField: UITextField!
    
    @IBOutlet weak var PhoneNumberTextField: UITextField!
    
    @IBOutlet weak var PasswordTextField: UITextField!
    
    @IBAction func RegisterButton(_ sender: Any)
    {
        guard let firstName  = FirstNameTextField.text,
              let lastName = LastNameTextField.text,
              let email = EmailTextField.text,
              let phone = PhoneNumberTextField.text,
              let password = PasswordTextField.text
                
        else
        {
            return
        }
        
        Task
        {
            let response = try await authSer.register(firstName: firstName,
                                                      lastName: lastName,
                                                      email: email,
                                                      password: password,
                                                      phone: phone)
            
            print(response.data)
            print("registration successful")
            
            await MainActor.run
            {
                let alert = UIAlertController(title: "success",
                                              message: "Registration successful",
                                              preferredStyle: .alert)
                
                alert.addAction(UIAlertAction(title: "OK", style: .default))
                
                self.navigationController?.popViewController(animated: true)
                
                self.present(alert, animated: true)
            }
        }
    }
    
    @objc
    func togglePassowrdVisibility(_ sender: UIButton)
    {
        sender.isSelected.toggle()
        PasswordTextField.isSecureTextEntry.toggle()
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
