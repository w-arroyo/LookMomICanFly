export class ProductSummary{

    id!: string;
    name!: string;
    year!: number;
    manufacturer!: string;
    category!: string;

    getId(): string {
        return this.id;
    }
    public setId(value: string) {
        this.id = value;
    }
    public getName(): string {
        return this.name;
    }
    public setName(value: string) {
        this.name = value;
    }
    public getYear(): number {
        return this.year;
    }
    public setYear(value: number) {
        this.year = value;
    }
    public getManufacturer(): string {
        return this.manufacturer;
    }
    public setManufacturer(value: string) {
        this.manufacturer = value;
    }
    public getCategory(): string {
        return this.category;
    }
    public setCategory(value: string) {
        this.category = value;
    }

}