//
//  UserViewController.m
//  ParseStarterProject
//
//  Created by Shaun Thompson on 7/17/15.
//
//

#import "UserViewController.h"
#import "Parse/Parse.h"
#import "ParseStarterProjectViewController.h"

@interface UserViewController ()

@end

@implementation UserViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    
    
    [self detectUser];
    
    
    
}




- (void) detectUser {
    
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
        // do stuff with the user
        //self.username = [NSString stringWithFormat:@"%@", currentUser];
        self.username = [NSString stringWithFormat:@"%@",[[PFUser currentUser]valueForKey:@"username"]] ;
        self.loginInfo.text = [NSString stringWithFormat:@"Logged In As: %@", self.username];
        
    } else {
        // show the signup or login screen
        
    }

}
- (IBAction)logOut:(id)sender {
    
    [self logOut];
}

- (void)logOut {
    
    [PFUser logOut];
    PFUser *currentUser = [PFUser currentUser]; // this will now be nil
    
    
    [self loadMainPage];
}

- (void) loadMainPage {
    
    ParseStarterProjectViewController *homePage = [[ParseStarterProjectViewController alloc] initWithNibName:nil bundle:nil];
    [self presentViewController:homePage animated:YES completion:NULL];
    //[self.navigationController pushViewController:userViewController animated:YES];
    //[detailViewController release];  // Do that if you don't ARC.
    
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
