//
//  UserViewController.h
//  ParseStarterProject
//
//  Created by Shaun Thompson on 7/17/15.
//
//

#import <UIKit/UIKit.h>
#import "Parse/Parse.h"

@interface UserViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (weak, nonatomic) IBOutlet UITextField *nameInput;
@property (weak, nonatomic) IBOutlet UITextField *numberInput;



@property (weak, nonatomic) IBOutlet UITableView *tableView;



@property (weak, nonatomic) IBOutlet UIButton *addButton;


@property (weak, nonatomic) IBOutlet UISwitch *editToggle;


@property (weak, nonatomic) IBOutlet UILabel *loginInfo;
@property (weak, nonatomic) IBOutlet UIButton *logOutButton;




@property NSString *username;
@property NSMutableArray *contactArray;
@property NSString *contactname;
@property NSString *contactnumber;
@property BOOL *valid;


//@property (weak, nonatomic) IBOutlet UITableView *contactTable;

@end
