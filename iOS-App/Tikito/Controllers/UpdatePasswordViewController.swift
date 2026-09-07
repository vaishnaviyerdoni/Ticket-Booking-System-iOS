//
//  UpdatePasswordViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//

import UIKit

class UpdatePasswordViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        setupPasswordField(CurrentPasswordTextField)
        setupPasswordField(NewPasswordTextField)
        setupPasswordField(ConfirmPasswordTextField)
    }
    
    @IBOutlet weak var CurrentPasswordTextField: UITextField!
    
    @IBOutlet weak var NewPasswordTextField: UITextField!
    
    @IBOutlet weak var ConfirmPasswordTextField: UITextField!
    
    @IBAction func UpdatePasswordButton(_ sender: Any) {

        let currentPassword =
            CurrentPasswordTextField.text ?? ""

        let newPassword =
            NewPasswordTextField.text ?? ""

        let confirmPassword =
            ConfirmPasswordTextField.text ?? ""


        guard !currentPassword.isEmpty else {

            showAlert(
                title: "Error",
                message: "Please enter your current password."
            )

            return
        }


        guard !newPassword.isEmpty else {

            showAlert(
                title: "Error",
                message: "Please enter a new password."
            )

            return
        }


        guard !confirmPassword.isEmpty else {

            showAlert(
                title: "Error",
                message: "Please confirm your new password."
            )

            return
        }


        guard newPassword == confirmPassword else {

            showAlert(
                title: "Error",
                message: "New passwords do not match."
            )

            return
        }


        Task {

            do {

                let authSer = AuthService()

                let response = try await authSer.updatePassword(
                    oldPassword: currentPassword,
                    newPassword: newPassword
                )

                print("Update Password API successful")
                print("Status:", response.status)
                print("Response:", response.data)


                await MainActor.run {

                    let alert = UIAlertController(
                        title: "Success",
                        message: "Password updated successfully.",
                        preferredStyle: .alert
                    )

                    alert.addAction(
                        UIAlertAction(
                            title: "OK",
                            style: .default
                        )
                    )

                    self.present(
                        alert,
                        animated: true
                    )
                }

            } catch {

                print("Update Password Failed:", error)

                await MainActor.run {

                    self.showAlert(
                        title: "Error",
                        message: "Failed to update password."
                    )
                }
            }
        }
    }
    
    func setupPasswordField(_ textField: UITextField)
    {

        textField.isSecureTextEntry = true

        let eyeButton = UIButton(type: .system)

        eyeButton.setImage(
            UIImage(systemName: "eye"),
            for: .normal
        )

        eyeButton.setImage(
            UIImage(systemName: "eye.slash"),
            for: .selected
        )

        eyeButton.addTarget(
            self,
            action: #selector(togglePasswordVisibility(_:)),
            for: .touchUpInside
        )

        textField.rightView = eyeButton
        textField.rightViewMode = .always
    }
    
    @objc
    func togglePasswordVisibility(_ sender: UIButton)
    {

        sender.isSelected.toggle()

        if let textField = sender.superview as? UITextField {
            textField.isSecureTextEntry.toggle()
        }
    }
    
    func showAlert(
        title: String,
        message: String
    ) {

        let alert = UIAlertController(
            title: title,
            message: message,
            preferredStyle: .alert
        )

        alert.addAction(
            UIAlertAction(
                title: "OK",
                style: .default
            )
        )

        present(
            alert,
            animated: true
        )
    }    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
