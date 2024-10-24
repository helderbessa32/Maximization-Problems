package Utils;

//! Class Pair
public class Pair<N, T> {

    N v1;
    T v2;

    //! Constructor
    //!
    //! \tparam N
    //! \tparam T
    //! \param vertex 1
    //! \param vertex 2
    public Pair(N v1, T v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    //! Get vertex 1
    //!
    //! \tparam N
    //! \return vertex 1
    public N getV1() {
        return v1;
    }
    //! Get vertex 1
    //!
    //! \tparam T
    //! \return vertex 2
    public T getV2() {
        return v2;
    }
}
