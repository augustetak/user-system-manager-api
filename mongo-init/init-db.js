db.auth('admin', 'Password123')
db.createUser(
    {
        user: "root",
        pwd: "Password123",
        roles: [
            {
                role: "readWrite",
                db: "user-system-manager-db"
            }
        ]
    }
);
