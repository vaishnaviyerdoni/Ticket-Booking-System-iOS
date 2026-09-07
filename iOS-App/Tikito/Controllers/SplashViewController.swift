//
//  SplashViewController.swift
//  Tikito
//
//  Created by Ashutosh Yerdoni on 31/08/26.
//

import UIKit

class SplashViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        DispatchQueue.main.asyncAfter(deadline: .now() + 2.0)
        {
            self.performSegue(withIdentifier : "showLogin", sender: self)
        }
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
