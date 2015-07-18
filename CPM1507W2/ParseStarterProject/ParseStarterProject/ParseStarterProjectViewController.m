//
//  ParseStarterProjectViewController.m
//
//  Copyright 2011-present Parse Inc. All rights reserved.
//

#import "ParseStarterProjectViewController.h"
#import "Parse/Parse.h"
#import "UserViewController.h"

@implementation ParseStarterProjectViewController

#pragma mark -
#pragma mark UIViewController


// IBACTIONS ------------------------------------

- (IBAction)login:(id)sender {
    
    self.username = self.luinput.text;
    self.password = self.lpinput.text;
    
    NSLog(@"You Logged %@",self.username);
    
    [self loginUser];
    
    
}

- (IBAction)register:(id)sender {
    
    self.username = self.ruinput.text;
    self.password = self.rpinput.text;
    
    NSLog(@"You Registered %@",self.ruinput.text);
    
    [self registerUser];
}

// ----------------------------------------------



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
    
    
    // Check to see if a user opted to stay logged in
    [self detectUser];
    
 
    

}

- (void) detectUser {
    
    PFUser *currentUser = [PFUser currentUser];
    if (currentUser) {
        
        NSLog(@"CURRENT USER");
        //self.username = [NSString stringWithFormat:@"%@",[[PFUser currentUser]valueForKey:@"username"]];
        //self.password = [NSString stringWithFormat:@"%@",[[PFUser currentUser]valueForKey:@"password"]];
        
        // bypass login screen
        [self loadUserPage];
        
    } else {
        // show the signup or login screen
        NSLog(@"NO CURRENT USER");
        
    }
}

- (void) loadUserPage {
    
    UserViewController *userViewController = [[UserViewController alloc] initWithNibName:nil bundle:nil];
    [self presentViewController:userViewController animated:YES completion:NULL];
    //[self.navigationController pushViewController:userViewController animated:YES];
    //[detailViewController release];  // Do that if you don't ARC.
    
}


// LOGIN USER -----------------------------------

- (void)loginUser {
    
    [PFUser logInWithUsernameInBackground:self.username password:self.password block:^(PFUser *user, NSError *error) {
        if (user) {
            
            // Do stuff after successful login.
            NSLog(@"Sucessful Login!");
            [self loadUserPage];
            
            
        }
        else {
            
            // The login failed. Check error to see why.
            NSLog(@"Login ERROR!");
            
        }
        
    }];
    
}
// -----------------------------------------------


// REGISTER USER ---------------------------------
- (void)registerUser {
    PFUser *user = [PFUser user];
    user.username = self.username;
    user.password = self.password;
    
    // other fields can be set just like with PFObject
    user[@"phone"] = @"415-392-0202";
    
    [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (!error) {   // Hooray! Let them use the app now.
            
            // call login method
            [self loginUser];
            
            
        }
        else {
            
            NSString *errorString = [error userInfo][@"error"];
            NSLog(@"%@", errorString);
        }
    }];
    
    
}
// ----------------------------------------------







- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


@end
