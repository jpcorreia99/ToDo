package view;

import todoApp.ToDo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ToDoPaginator {
    /** ArrayList who stores each sub_array of To_do's.toString()*/
    private List<List<String>> paginatorLists;

    /** Number of todo's by page*/
    private int pageSize = 3;

    /** Index of the current page*/
    private int currentPage;

    /** Number of total pages*/
    private int numPages;


    public ToDoPaginator(Collection<ToDo> col){
        this.currentPage=0;
        this.numPages = 0;
        this.paginatorLists = new ArrayList<>();

        List<String>listOAllToDos= new ArrayList<String>();

        for(ToDo td : col){
            listOAllToDos.add(td.toString());
        }

        if(listOAllToDos.isEmpty())
        {
            ArrayList<String> aux = new ArrayList<>();
            aux.add("There are no ToDo's");
            paginatorLists.add(aux);
            this.numPages = 1;
        }
        else
        {
            Iterator<String> it2 = listOAllToDos.iterator();

            while(it2.hasNext()){
                ArrayList<String> aux = new ArrayList<>();
                for(int i=0; i<this.pageSize && it2.hasNext();i++){
                    aux.add(it2.next());
                }
                paginatorLists.add(aux);
                numPages+=1;
            }
        }
    }

    public int getCurrentPage(){return this.currentPage;}
    public int getNumPages(){return  this.numPages;}

    public List<String> getCurrentPageList(){
        return this.paginatorLists.get(this.currentPage);
    }

    public void nextPage() {
        if(this.currentPage+1<this.numPages) currentPage+=1;
    }

    public void beforePage() {
        if(this.currentPage>0) currentPage-=1;
    }


}
