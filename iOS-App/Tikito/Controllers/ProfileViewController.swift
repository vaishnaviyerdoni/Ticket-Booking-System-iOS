//
//  ProfileViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 02/09/26.
//

import UIKit

class ProfileViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func logoutBtn(_ sender: Any)
    {
        print("Logout btn clicked")
        let keyChain = KeychainService()
        
        //deleting jwt
        keyChain.deleteToken()
        
        //Testing if token was deleted
        if(keyChain.getToken() == nil)
        {
            print("Token is deleted")
        }
        
        //Back to login
        navigationController?.popToRootViewController(animated: true)    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
