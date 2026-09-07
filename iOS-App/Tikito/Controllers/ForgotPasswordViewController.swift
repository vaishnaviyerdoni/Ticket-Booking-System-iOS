//
//  ForgotPasswordViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//
import UIKit
class ForgotPasswordViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    var email: String?

    @IBOutlet weak var EmailTextField: UITextField!

    @IBAction func SentOTPButton(_ sender: Any) {

        let email = EmailTextField.text ?? ""

        // Check email is not empty
        guard !email.isEmpty else {

            let alert = UIAlertController(
                title: "Error",
                message: "Please enter your email.",
                preferredStyle: .alert
            )

            alert.addAction(
                UIAlertAction(title: "OK", style: .default)
            )

            present(alert, animated: true)

            return
        }

        Task {
            do {

                print("Calling Forgot Password API...")
                print("Email:", email)

                let authSer = AuthService()

                let response = try await authSer.forgetPassword(
                    email: email
                )

                print("Forgot Password API successful")
                print("Status:", response.status)
                print("Response:", response.data)

                await MainActor.run {

                    let alert = UIAlertController(
                        title: "Success",
                        message: "OTP sent successfully.",
                        preferredStyle: .alert
                    )

                    // IMPORTANT:
                    // Segue happens ONLY after user presses OK
                    alert.addAction(
                        UIAlertAction(
                            title: "OK",
                            style: .default
                        ) { _ in

                            self.performSegue(
                                withIdentifier: "toResetPage",
                                sender: email
                            )
                        }
                    )

                    self.present(
                        alert,
                        animated: true
                    )
                }

            } catch {

                print("Forgot Password Failed:", error)

                await MainActor.run {

                    let alert = UIAlertController(
                        title: "Error",
                        message: "Failed to send OTP.",
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
            }
        }
    }

    // MARK: - Navigation

    override func prepare(
        for segue: UIStoryboardSegue,
        sender: Any?
    ) {

        if segue.identifier == "toResetPage" {

            let destination =
                segue.destination as! ResetPasswordViewController

            destination.email = sender as? String

            print(
                "Email passed to Reset Password:",
                destination.email ?? "nil"
            )
        }
    }
}
