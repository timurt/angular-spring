export class Pagination {
    currentPage : number = 1;
    totalPages : number = 1;
    range : number[] = [];
    size : number = 10;
    hasNext : boolean = true;
    hasPrev : boolean = false;
}