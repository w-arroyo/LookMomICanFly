export class Product{

    id!: string;
    name!: string;
    category!: string;
    subcategory!: string;
    manufacturer!: string;
    releaseYear!: number;
    active!: boolean;
    colors!: string[];
    
    public getId(): string {
        return this.id;
    }
    public setId(value: string) {
        this.id = value;
    }
    public getColors(): string[] {
        return this.colors;
    }
    public setColors(value: string[]) {
        this.colors = value;
    }
    public getActive(): boolean {
        return this.active;
    }
    public setActive(value: boolean) {
        this.active = value;
    }
    public getReleaseYear(): number {
        return this.releaseYear;
    }
    public setReleaseYear(value: number) {
        this.releaseYear = value;
    }
    public getManufacturer(): string {
        return this.manufacturer;
    }
    public setManufacturer(value: string) {
        this.manufacturer = value;
    }
    public getSubcategory(): string {
        return this.subcategory;
    }
    public setSubcategory(value: string) {
        this.subcategory = value;
    }
    public getName(): string {
        return this.name;
    }
    public setName(value: string) {
        this.name = value;
    }
    public getCategory(): string {
        return this.category;
    }
    public setCategory(value: string) {
        this.category = value;
    }
}