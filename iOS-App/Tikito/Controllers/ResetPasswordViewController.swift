//
//  ResetPasswordViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//

import UIKit
class ResetPasswordViewController: UIViewController {

    // Email received from Forgot Password screen
    var email: String?

    override func viewDidLoad() {
        super.viewDidLoad()

        print("Reset Password Screen Loaded")
        print("Email:", email ?? "nil")
    }

    @IBOutlet weak var OTPTextField: UITextField!

    @IBOutlet weak var NewPasswordTextField: UITextField!

    @IBOutlet weak var ConfirmPasswordTextField: UITextField!

    @IBAction func ResetBtn(_ sender: Any) {

        // MARK: - Get values

        let otpText = OTPTextField.text ?? ""
        let newPassword = NewPasswordTextField.text ?? ""
        let confirmPassword = ConfirmPasswordTextField.text ?? ""

        // MARK: - Check email

        guard let email = email, !email.isEmpty else {

            showAlert(
                title: "Error",
                message: "Email is missing."
            )

            return
        }

        // MARK: - Check OTP

        guard !otpText.isEmpty else {

            showAlert(
                title: "Error",
                message: "Please enter the OTP."
            )

            return
        }

        // Convert OTP String → Int
        guard let otp = Int(otpText) else {

            showAlert(
                title: "Error",
                message: "OTP must contain numbers only."
            )

            return
        }

        // MARK: - Check New Password

        guard !newPassword.isEmpty else {

            showAlert(
                title: "Error",
                message: "Please enter a new password."
            )

            return
        }

        // MARK: - Check Confirm Password

        guard !confirmPassword.isEmpty else {

            showAlert(
                title: "Error",
                message: "Please confirm your password."
            )

            return
        }

        // MARK: - Check passwords match

        guard newPassword == confirmPassword else {

            showAlert(
                title: "Error",
                message: "Passwords do not match."
            )

            return
        }

        // MARK: - Call Reset Password API

        Task {

            do {

                let authSer = AuthService()

                let response = try await authSer.resetPassword(
                    email: email,
                    otp: otp,
                    newPassword: newPassword
                )

                print("Reset Password API successful")
                print("Status:", response.status)
                print("Response:", response.data)

                await MainActor.run {

                    let alert = UIAlertController(
                        title: "Success",
                        message: "Password reset successfully.",
                        preferredStyle: .alert
                    )

                    alert.addAction(
                        UIAlertAction(
                            title: "OK",
                            style: .default
                        ) { _ in

                            // Go back to Login
                            self.navigationController?.popViewController(
                                animated: true
                            )
                        }
                    )

                    self.present(alert, animated: true)
                }

            } catch {

                print("Reset Password Failed:", error)

                await MainActor.run {

                    self.showAlert(
                        title: "Error",
                        message: "Failed to reset password."
                    )
                }
            }
        }
    }

    // MARK: - Alert Helper

    private func showAlert(
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

        present(alert, animated: true)
    }
}
