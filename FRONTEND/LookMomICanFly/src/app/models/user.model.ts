export class User{

    private id!: string;
    private name!: string;
    private email!: string;
    private password!: string;
    private userType!: string;
    
    public getUserType(): string {
        return this.userType;
    }
    public setUserType(value: string) {
        this.userType = value;
    }
    
    public getPassword(): string {
        return this.password;
    }
    public setPassword(value: string) {
        this.password = value;
    }

    public getEmail(): string {
        return this.email;
    }
    public setEmail(value: string) {
        this.email = value;
    }

    public getName(): string {
        return this.name;
    }
    public setName(value: string) {
        this.name = value;
    }

    public getId(): string {
        return this.id;
    }
    public setId(value: string) {
        this.id = value;
    }

}