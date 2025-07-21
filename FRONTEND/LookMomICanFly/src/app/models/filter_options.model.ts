export class FilterSelectedOptions{

    subcategories:string[]=[];
    years:number[]=[];
    colors:string[]=[];
    manufacturers:string[]=[];

    clearOptions(){
        this.subcategories=[];
        this.years=[];
        this.colors=[];
        this.manufacturers=[];
    }

    checkIfEmpty(){
        return this.subcategories.length==0 && this.years.length==0 && this.colors.length==0 && this.manufacturers.length==0;
    }

}