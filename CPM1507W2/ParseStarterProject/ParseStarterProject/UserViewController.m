//
//  UserViewController.m
//  ParseStarterProject
//
//  Created by Shaun Thompson on 7/17/15.
//
//

#import "UserViewController.h"
#import "ParseStarterProjectViewController.h"

@interface UserViewController ()

@end

@implementation UserViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    
    // determine user & parse info
    [self detectUser];
    
    // determine size of object array
    
    // query parse data
    [self performSelector:@selector(queryParse)];
    
    // convert object to string & populate string array
    
    // display string array in table
    
    
    
}


- (void) queryParse {
    
    PFQuery *query = [PFQuery queryWithClassName:self.username];
    [query findObjectsInBackgroundWithBlock:^(NSArray *objects, NSError *error) {
        
        if (!error){
            self.contactArray = [[NSArray alloc] initWithArray:objects];
        }
        
        NSLog(@"%@", self.contactArray);
        [self.tableView reloadData];
        
    }];
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


- (IBAction)addContact:(id)sender {
    
    NSString *name = self.nameInput.text;
    NSString *number = self.numberInput.text;
    NSNumberFormatter *format = [[NSNumberFormatter alloc] init];
    format.numberStyle = NSNumberFormatterDecimalStyle;
    NSNumber *convert = [format numberFromString:number];
    
    
    PFObject *contact = [PFObject objectWithClassName:self.username];
    contact[@"name"] = name;
    contact[@"number"] = convert;
    
    [contact saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (succeeded) {
            // The object has been saved.
            NSLog(@"%@ has been added", name);
        } else {
            // There was a problem, check error.description
        }
    }];
    
    self.nameInput.text = @"";
    self.numberInput.text = @"";
    
    // TODO ------------------------ REFRESH OR UPDATE TABLE VIEW
    
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

// TABLE VIEW ITEMS ------------------------------

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}



- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.contactArray.count;
}



- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"MainCell"];
    
    if(cell == nil){
        
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"MainCell"];
    }
    
    //cell.textLabel.text = @"Some text";
    
    PFObject *parseObj = [self.contactArray objectAtIndex:indexPath.row];
    cell.textLabel.text = [parseObj objectForKeyedSubscript:@"name"];
    
    
    return cell;
    
    
}

// -----------------------------------------------


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
